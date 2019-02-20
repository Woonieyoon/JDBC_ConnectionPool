package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageNotFoundException extends RuntimeException  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PageNotFoundException.class);
	private static final long serialVersionUID = -9098326003920875948L;

	public PageNotFoundException() {
		LOGGER.error("ã���� ���� ������ �Դϴ�.");
	}

	public PageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PageNotFoundException(String message) {
		super(message);
	}

	public PageNotFoundException(Throwable cause) {
		super(cause);
	}

}
