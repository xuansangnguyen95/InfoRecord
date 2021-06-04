package commons;

public class GlobalConstants {
	
	public static final long SHORT_TIMEOUT = 5;	
	public static final long LONG_TIMEOUT = 30;	
	
	
	public static final String OS_NAME = System.getProperty("os.name");	
	public static final String ROOT_FOLDER = System.getProperty("user.dir");
	
	public static final String BROWSER_LOG_FOLDER = ROOT_FOLDER + getDirectorySlash("browserLog");
	public static final String DOWNLOAD_FOLDER = ROOT_FOLDER + getDirectorySlash("downloadFiles");
	public static final String UPLOAD_FOLDER = ROOT_FOLDER + getDirectorySlash("uploadFiles");;
	

	
	public static String getDirectorySlash(String folderName) {
		if (isMac() || isUnix() || isSolaris()) {
			folderName = "/" + folderName + "/";
		} else {
			folderName = "\\" + folderName + "\\";
		}
		return folderName;
	}

	public static boolean isWindows() {
		return (GlobalConstants.OS_NAME.toLowerCase().indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (GlobalConstants.OS_NAME.toLowerCase().indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (GlobalConstants.OS_NAME.toLowerCase().indexOf("nix") >= 0 || GlobalConstants.OS_NAME.toLowerCase().indexOf("nux") >= 0 || GlobalConstants.OS_NAME.toLowerCase().indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (GlobalConstants.OS_NAME.toLowerCase().indexOf("sunos") >= 0);
	}
}