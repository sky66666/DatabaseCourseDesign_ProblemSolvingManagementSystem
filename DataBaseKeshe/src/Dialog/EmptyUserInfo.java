package Dialog;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import org.eclipse.swt.SWT;

public class EmptyUserInfo {

    protected Shell shell;

    public static void main(String[] args){
        try {
            EmptyUserInfo window = new EmptyUserInfo();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open(){
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

    protected void createContents() {
        shell = new Shell();
        shell.setSize(240, 120);
        shell.setText("Error:001");
        shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2, Display.getCurrent()
                .getClientArea().height / 2 - shell.getSize().y / 2);

        Label label = new Label(shell, SWT.CENTER);
        label.setText("用户名或密码不能为空!");
        label.setBounds(20, 28, 180, 23);

    }
}
