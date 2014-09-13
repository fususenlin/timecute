package com.matrixloop.timecute.utils.log;



import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public enum SLogger {

	;

	private static String FQCN ;

	private static Logger log ;

	private static boolean isDebugEnabled ;

	private static boolean isInfoEnabled ;

	private static boolean isTraceEnabled ;

	static {
		FQCN = SLogger.class.getName() ;
		log = Logger.getLogger(SLogger.class.getName()) ;
		isDebugEnabled = log.isDebugEnabled() ;
		isInfoEnabled = log.isInfoEnabled() ;
		isTraceEnabled = log.isTraceEnabled() ;
	}

	public static void info(Object message) {
		info(message, null);
	}

	public static void info(Object message, Throwable t) {
		if (isInfoEnabled) {
			log.log(FQCN, Level.INFO, message, t);
		}
	}

	public static void debug(Object message) {
		debug(message, null);
	}

	public static void debug(Object message, Throwable t) {
		if (isDebugEnabled) {
			log.log(FQCN, Level.DEBUG, message, t);
		}
	}

	public static void warn(Object message) {
		warn(message, null);
	}

	public static void warn(Object message, Throwable t) {
		log.log(FQCN, Level.WARN, message, t);
	}

	public static void error(Object message) {
		error(message, null);
	}

	public static void error(Object message, Throwable t) {
		log.log(FQCN, Level.ERROR, message, t);
	}

	public static void trace(Object message) {
		trace(message, null);
	}

	public static void trace(Object message, Throwable t) {
		if (isTraceEnabled) {
			log.log(FQCN, Level.TRACE, message, t);
		}
	}

	public static void fatal(Object message) {
		fatal(message, null);
	}

	public static void fatal(Object message, Throwable t) {
		log.log(FQCN, Level.FATAL, message, t);
	}
}
