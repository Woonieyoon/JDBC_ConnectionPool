package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuplicationException extends RuntimeException {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DuplicationException.class);
	private static final long serialVersionUID = -5501524881597863222L;

	public DuplicationException() {
		LOGGER.error("Primary key 중복발생");
	}

	public DuplicationException(String message, Throwable cause) {
	}

	public DuplicationException(String message) {
	}

	public DuplicationException(Throwable cause) {
	}

	
}
