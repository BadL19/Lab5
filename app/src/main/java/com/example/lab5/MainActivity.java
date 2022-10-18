http://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html
        */
//TODO 1
//TODO 2
//TODO 3
//TODO 4
//TODO 5

public class MainActivity extends AppCompatActivity {

    ArrayList<Todo> todoItems;
    ArrayAdapter<Todo> aTodoAdapter;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        aTodoAdapter = new TodoAdapter(this, todoItems);
        lvItems.setAdapter(aTodoAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        todoItems.remove(pos);
                        // Refresh the adapter
                        aTodoAdapter.notifyDataSetChanged();
                        writeItems();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }

    public void onAddItem(View v) {
        int todoid = 0;
        EditText etNewItem = (EditText) findViewById(R.id.editTextCreate);
        String itemText = etNewItem.getText().toString();
        il urgency=Il.neither;
        if (((RadioButton) findViewById (R.id.radioButtonLow)).isChecked()) {
            urgency = Il.low;
        }
        else if (((RadioButton) findViewById (R.id.radioButtonHigh)).isChecked()) {
            urgency = Il.high;
        }

        Todo todo = new Todo(todoid,itemText,urgency);
        aTodoAdapter.add(todo);
        etNewItem.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<Todo>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            todoItems = new ArrayList<Todo>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, todoItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}