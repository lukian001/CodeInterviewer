package main.gui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

import main.customs.InputField;
import main.enums.ControlDimensions;
import main.parents.Dialog;

public class ModularDialog extends Dialog {

	private String[] titles;
	private String[] buttonNames;
	private int[] styles;
	private List<InputField> fields;
	private List<Button> buttons;
	private Label errorMessage;

	public ModularDialog(String title, String[] titles, int[] styles, String[] buttonNames) {
		super(title, false);
		this.titles = titles;
		this.styles = styles;
		this.buttonNames = buttonNames;

		this.fields = new ArrayList<>();
		this.buttons = new ArrayList<>();
	}

	@Override
	public void createDialog() {
		this.shell.setLayout(new RowLayout(SWT.VERTICAL));
		for (int i = 0; i < titles.length; i++) {
			InputField field = new InputField(shell, styles[i], ControlDimensions.INPUT_FIELD_WIDTH, //
					ControlDimensions.INPUT_FIELD_HEIGHT, true);
			field.setText(titles[i]);
			fields.add(field);
		}

		errorMessage = new Label(shell, SWT.NONE);
		errorMessage.setText("-".repeat(62));
		errorMessage.setVisible(false);

		for (String name : buttonNames) {
			buttons.add(new Button(shell, SWT.NONE));
			buttons.get(buttons.size() - 1).setText(name);
		}
	}

	public void setDisabled(int index) {
		fields.get(index).getField().setEnabled(false);
	}

	public void fill(String[] content) {
		for (int i = 0; i < content.length; i++) {
			fields.get(i).getField().setText(content[i]);
		}
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public List<InputField> getFields() {
		return fields;
	}

	public void setErrorMessage(String message) {
		this.errorMessage.setText(message);
		this.errorMessage.setVisible(true);
	}
}
