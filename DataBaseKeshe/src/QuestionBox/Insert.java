package QuestionBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

//import system.dbo;


public class Insert {

    public Boolean isAdmin,avaliable;
    public String userid,problemid,cost,result;

    protected Shell shell;

    public static void main(String[] args) {
        try {
            Insert window = new Insert();
            window.open(false,"2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open(boolean isAdmin,String userid) {
        this.isAdmin=isAdmin;
        this.userid=userid;
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
        shell.setSize(280, 307);
        shell.setText("插入数据");
        shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2, Display.getCurrent()
                .getClientArea().height / 2 - shell.getSize().y / 2);

        Label label1 = new Label(shell,SWT.NONE);
        label1.setBounds(30,20,60,23);
        label1.setText("用户编号");

        Text text1 = new Text(shell,SWT.NONE);
        text1.setBounds(95,20,150,23);
        text1.setText(userid);
        if(!isAdmin)text1.setEditable(false);

        Label label2 = new Label(shell,SWT.NONE);
        label2.setBounds(30,60,60,23);
        label2.setText("题目编号");

        Text text2 = new Text(shell,SWT.NONE);
        text2.setBounds(95,60,150,23);

        Label label3 = new Label(shell,SWT.NONE);
        label3.setBounds(30,100,60,23);
        label3.setText("评测结果");

        Text text3 = new Text(shell,SWT.NONE);
        text3.setBounds(95,100,150,23);

        Label label4 = new Label(shell,SWT.NONE);
        label4.setBounds(30,140,60,23);
        label4.setText("测评耗时");

        Text text4 = new Text(shell,SWT.NONE);
        text4.setBounds(95,140,150,23);


        Button button = new Button(shell, SWT.CENTER);
        button.setBounds(60,190,150,40);
        button.setText("插入");
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                String s1 = text1.getText();
                for(int i=0;i<s1.length();++i){
                    if(!Character.isDigit(s1.charAt(i))){
                        UnavaliableFilter window = new UnavaliableFilter();
                        window.open("用户编号");
                        return;
                    }
                }
                if(s1.length()==0){
                    UnavaliableFilter window = new UnavaliableFilter();
                    window.open("用户编号");
                    return;
                }

                String s2 = text2.getText();
                for(int i=0;i<s2.length();++i){
                    if(!Character.isDigit(s2.charAt(i))){
                        UnavaliableFilter window = new UnavaliableFilter();
                        window.open("题目编号");
                        return;
                    }
                }
                if(s2.length()==0){
                    UnavaliableFilter window = new UnavaliableFilter();
                    window.open("题目编号");
                    return;
                }

                String s3 = text4.getText();
                for(int i=0;i<s3.length();++i){
                    if(!Character.isDigit(s3.charAt(i))){
                        UnavaliableFilter window = new UnavaliableFilter();
                        window.open("测评耗时");
                        return;
                    }
                }
                if(s3.length()==0){
                    UnavaliableFilter window = new UnavaliableFilter();
                    window.open("测评耗时");
                    return;
                }

                String s4 = text3.getText();
                if(!(s4.equals("ACCEPTED")||s4.equals("RUN TIME ERROR")||s4.equals("WRONG ANSWER")||s4.equals("TIME LIMITED ERROR")||s4.equals("MEMORY LIMITED ERROR"))){
                    UnavaliableFilter window = new UnavaliableFilter();
                    window.open("评测结果");
                    return;
                }

                userid = text1.getText();
                problemid = text2.getText();
                cost = text4.getText();
                result = s4;
                avaliable = true;

                shell.setVisible(false);
                shell.dispose();
            }
        });

    }
}
