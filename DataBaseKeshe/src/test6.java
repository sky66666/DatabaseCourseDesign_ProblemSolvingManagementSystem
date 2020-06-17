import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import org.eclipse.swt.SWT;

//import system.dbo;


public class test6 {

    protected Shell shell;
    private Table table;

    public static void main(String[] args) {
        try {
            test6 window = new test6();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell();
        shell.setSize(851, 777);
        shell.setText("员工基本信息");
        shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2, Display.getCurrent()
                .getClientArea().height / 2 - shell.getSize().y / 2);

        table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        table.setBounds(28, 147, 799, 543);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        TableColumn tc1 = new TableColumn(table, SWT.CENTER);
        TableColumn tc2 = new TableColumn(table, SWT.CENTER);
        TableColumn tc3 = new TableColumn(table, SWT.CENTER);
        TableColumn tc4 = new TableColumn(table, SWT.CENTER);
        TableColumn tc5 = new TableColumn(table, SWT.CENTER);
        TableColumn tc6 = new TableColumn(table, SWT.CENTER);
        TableColumn tc7 = new TableColumn(table, SWT.CENTER);
        TableColumn tc8 = new TableColumn(table, SWT.CENTER);
        TableColumn tc9 = new TableColumn(table, SWT.CENTER);
        tc1.setText("工号");
        tc2.setText("姓名");
        tc3.setText("性别");
        tc4.setText("年龄");
        tc5.setText("学历");
        tc6.setText("联系电话");
        tc7.setText("所属部门");
        tc8.setText("职务");
        tc9.setText("是否退休");
        tc1.setWidth(80);
        tc2.setWidth(80);
        tc3.setWidth(80);
        tc4.setWidth(80);
        tc5.setWidth(80);
        tc6.setWidth(80);
        tc7.setWidth(80);
        tc8.setWidth(80);
        tc9.setWidth(80);

        Text text;
        text = new Text(shell, SWT.BORDER);
        text.setBounds(107, 10, 79, 23);

        final String[] s = {""};


        Button button_1 = new Button(shell, SWT.NONE);
        button_1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                table.clear(5);
                table.getItem(5).setText(String.valueOf(new char[]{'1', '2'}));
            }
        });

        //返回主菜单
        button_1.addMouseListener(new MouseAdapter(){
              public void mouseDown(MouseEvent e){
                  s[0] = text.getText();
                  TableItem item1=new TableItem(table,SWT.NONE);
                  item1.setText(s);
              }
          }
        );
        button_1.setBounds(707, 696, 80, 27);
        button_1.setText("shabi");

    }
}
