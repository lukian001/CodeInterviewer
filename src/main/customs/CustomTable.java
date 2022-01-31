package main.customs;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import main.enums.ControlDimensions;
import main.gui.MainDialog;
import main.gui.SessionOpenDialog;
import main.model.Model;
import main.model.SessionStatus;
import main.parents.Dialog;

public class CustomTable {
	private Table table;

	public CustomTable(Shell shell, boolean action, int width, Dialog startedIn) {
		table = new Table(shell, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(ControlDimensions.INPUT_FIELD_WIDTH.getValue() + 3, width));

		if (action) {
			table.addListener(SWT.MouseDoubleClick, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					if (table.getSelection()[0].getText(table.getColumnCount() - 1).equals("ON")) {
						new SessionOpenDialog(table.getSelection()[0], ((MainDialog) startedIn).getUsername()).run();
					}
				}

			});
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

	public void setColumns(String[] columns) {
		for (String column : columns) {
			new TableColumn(table, SWT.NONE).setText(column);
		}
	}

	public void fill(List<? extends Model> filling) {
		for (Model fill : filling) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(fill.getData());

			Color red = table.getDisplay().getSystemColor(SWT.COLOR_YELLOW);
			Color green = table.getDisplay().getSystemColor(SWT.COLOR_GREEN);
			Color blue = table.getDisplay().getSystemColor(SWT.COLOR_RED);

			if (item.getText(3).equals(SessionStatus.OFF.toString())) {
				item.setBackground(3, red);
			} else if (item.getText(3).equals(SessionStatus.DONE.toString())) {
				item.setBackground(3, blue);
			} else if (item.getText(3).equals(SessionStatus.ON.toString())) {
				item.setBackground(3, green);
			}
		}
		TableColumn[] columns = table.getColumns();

		int size = 0;
		columns[0].pack();
		size += columns[0].getWidth();
		for (int i = 2; i < columns.length; i++) {
			columns[i].pack();
			size += columns[i].getWidth();
		}

		if (columns.length > 1) {
			columns[1].setWidth(ControlDimensions.INPUT_FIELD_WIDTH.getValue() + 3 - size);
		}
	}

	public void redraw(List<? extends Model> fill) {
		table.removeAll();
		fill(fill);
	}

}
