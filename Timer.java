package application;
/**
 * This class is used to keep track of the passing of time.
 * @author William Conroy
 */
public class Timer {
	//To be used to convert millis to seconds
	private static final int MILL_TO_SEC = 1000;
	//When the timer started
	private long start;
	//When the timer stopped
	private long end;
	//How long the table was running
	private long length;
	//Where or not the timer has been stopped
	private boolean isStopped;
	
	//time to be add
	private int savedTime;
	
	Timer() {
		this.start = System.currentTimeMillis();
		this.isStopped = false;
		this.savedTime = 0;
	}
	
	Timer(int savedTime) {
		this.start = System.currentTimeMillis();
		this.isStopped = false;
		this.savedTime = savedTime;
	}
	
	/**
	 * Converts a number form millis to seconds.
	 * @return The conversion.
	 */
	private int convertion() {
		return (int) (this.getTimeMill() / MILL_TO_SEC) + savedTime;
	}
	
	/**
	 * Stops the timer.
	 * @return length in seconds the timer ran.
	 */
	public int stop() {
		this.end =  System.currentTimeMillis();
		this.length = this.end - this.start;
		return convertion();
	}
	
	/**
	 * Get the time that the timer has been running.
	 * @return length in seconds the timer ran.
	 */
	public long getTimeMill() {
		if (this.isStopped) {
			return this.length;
		} else {
			this.length = System.currentTimeMillis() - this.start;
			return this.length;
		}
	}
	
	/**
	 * Gets the time form the start of timer.
	 * @return time in seconds
	 */
	public int getTime() {
		if (this.isStopped) {
			return convertion();
		} else {
			this.length = System.currentTimeMillis() - this.start;
			return convertion();
		}
	}
}
