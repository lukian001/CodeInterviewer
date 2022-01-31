package main.customs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;

import main.gui.SessionDialog;
import main.parents.Dialog;

public class CustomCalendar extends Dialog {

	private SessionDialog sessionDialog;

	public CustomCalendar(String title, SessionDialog sessionDialog) {
		super(title, false);
		this.sessionDialog = sessionDialog;
	}

	@Override
	public void createDialog() {
		shell.setLayout(new GridLayout(1, false));

		final DateTime calendar = new DateTime(shell, SWT.CALENDAR | SWT.BORDER);
		final DateTime time = new DateTime(shell, SWT.TIME | SWT.SHORT);
		time.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		ok.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sessionDialog.getTimeButton().setText((calendar.getMonth() + 1) + "/" + calendar.getDay() + "/"
						+ calendar.getYear() + " - " + time.getHours() + ":" + time.getMinutes());
				shell.close();
			}
		});
		shell.setDefaultButton(ok);

	}

}
