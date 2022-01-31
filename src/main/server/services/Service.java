package main.server.services;

import java.io.Serializable;

public abstract class Service implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7822136275263764096L;

	private ServiceStatus status;
	private int time;
	@SuppressWarnings("unused")
	private String name;

	public Service(String name, int seconds) {
		this.time = seconds * 1000;
		this.name = name;
	}

	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				status = ServiceStatus.ON;

				try {
					while (status != ServiceStatus.OFF) {
						if (status == ServiceStatus.ON) {
							doServiceAction();
						}
						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
						}
					}
				} catch (Exception e) {
					status = ServiceStatus.ERROR;
				}
			}
		}).start();
	}

	protected abstract void doServiceAction();

}
