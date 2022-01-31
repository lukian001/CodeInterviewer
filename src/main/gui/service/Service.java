package main.gui.service;

public abstract class Service {

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
					while (status != ServiceStatus.OFF && returnSecondCondition()) {
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
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void pauseService() {
		this.status = ServiceStatus.PAUSED;
	}

	public void startService() {
		this.status = ServiceStatus.ON;
	}

	public void stopService() {
		this.status = ServiceStatus.OFF;
	}

	protected abstract void doServiceAction();

	protected abstract boolean returnSecondCondition();
}
