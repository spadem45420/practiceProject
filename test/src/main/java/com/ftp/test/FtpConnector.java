
package com.ftp.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileFilter;
import org.apache.commons.vfs2.FileFilterSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.FileUtil;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;

public class FtpConnector {
	
	private FTPConnectionInfo info = null;
	
	private FileSystemManager fileSystemManager = null;
	
	private static Log logger = LogFactory.getLog(FtpConnector.class);
	
	public FTPConnectionInfo getConnectionInfo() {
		return info;
	}

	public FtpConnector( FTPConnectionInfo info ) throws Exception {
		if ( null == info.getProtocol() ){
			info.setProtocol(FTPProtocol.FTP);
		}
		this.info = info;
		this.fileSystemManager = VFS.getManager();
	}
	
	public void download( String url, File localDirectory ) {
		try {
			
			if( null == localDirectory || !localDirectory.isDirectory() || !localDirectory.exists() ) {
				logger.info("Illegal Output File System.");
			}
			
			String protocol = null;
			if ( FTPProtocol.FTP == info.getProtocol() ) {
				protocol = "ftp://";
			} else if ( FTPProtocol.SFTP == info.getProtocol() ) {
				protocol = "sftp://";
			}
			String realPath = String.format("%s%s:%s@%s/%s", protocol, info.getUser(), info.getPssd(), info.getHost(), url);
			logger.info("Begin Connect To : " + realPath );

			FileObject remoteFileNode = null;
			try {
				remoteFileNode = fileSystemManager.resolveFile(realPath);
			} catch ( Exception e ) {
				logger.info("Could Not Connect To" + realPath);
				logger.info( e.getMessage() );
				return;
			}
			
			logger.info("Connect To : " + realPath + " Success");
			
			if ( FileType.FOLDER == remoteFileNode.getType() ) {
				logger.info("Remote Node Is Directory, Begin Download All Files");
				for( FileObject childNode : remoteFileNode.getChildren()) {
					logger.info("Begin Download : " + childNode.getName() );
					try {
						// File 改為SafeFile FindBugs錯誤 ----- Kyle 2015/12/04
						File localFile = new File( localDirectory, remoteFileNode.getName().getBaseName() );
						FileObject localFileObject = fileSystemManager.resolveFile( localFile.getAbsolutePath() );
						localFileObject.copyFrom( remoteFileNode, Selectors.SELECT_ALL );

					} catch(FileSystemException e) {
						logger.info("Download : " + childNode.getName() + " Error, Continue Download Next Resource");
					}
				}
				
			} else if ( FileType.FILE == remoteFileNode.getType()) {
				logger.info("Remote Node Is File, Begin Download File");
				// File 改為SafeFile FindBugs錯誤 ----- Kyle 2015/12/04
				File localFile = new File( localDirectory, remoteFileNode.getName().getBaseName() );
				FileObject localFileObject = fileSystemManager.resolveFile( localFile.getAbsolutePath() );
				localFileObject.copyFrom( remoteFileNode, Selectors.SELECT_FILES );					
			}
			logger.info("Ftp Download Complete");
		} catch (FileSystemException e) {			
			e.printStackTrace();
		} 
	}

	public void searchDownload( String url, final String regex, File localDirectory ) {
		try {
			
			if( null == localDirectory || !localDirectory.isDirectory() || !localDirectory.exists() ) {
				logger.info("Illegal Output File System.");
			}
			
			String protocol = null;
			if ( FTPProtocol.FTP == info.getProtocol() ) {
				protocol = "ftp://";
			} else if ( FTPProtocol.SFTP == info.getProtocol() ) {
				protocol = "sftp://";
			}
			String realPath = String.format("%s%s:%s@%s/%s", protocol, info.getUser(), info.getPssd(), info.getHost(), url);
			logger.info("Begin Connect To : " + realPath );

			FileObject remoteFileNode = null;
			try {
				remoteFileNode = fileSystemManager.resolveFile(realPath);
			} catch ( Exception e ) {
				logger.info("Could Not Connect To" + realPath);
				logger.info( e.getMessage() );
				return;
			}
			
			logger.info("Connect To : " + realPath + " Success");
			
			if ( FileType.FOLDER == remoteFileNode.getType() ) {
				
				
				final FileFilter fileFilter = new FileFilter() {
				    public boolean accept(FileSelectInfo fileInfo) {
				        FileObject fo = fileInfo.getFile();
				        System.out.println( fo.getName().getBaseName() );
				        System.out.println( fo.getName().getBaseName().matches(regex) );
				        return fo.getName().getBaseName().matches(regex);
				    }				                
				};
				
				FileObject[] childNodes = remoteFileNode.findFiles( new FileFilterSelector(fileFilter) );
				
				logger.info("Remote Node Is Directory, Begin Download All Files");
				for( FileObject childNode : childNodes ) {
					logger.info("Begin Download : " + childNode.getName() );
					try {
						// File 改為SafeFile FindBugs錯誤 ----- Kyle 2015/12/04
						File localFile = new File( localDirectory, remoteFileNode.getName().getBaseName() );
						FileObject localFileObject = fileSystemManager.resolveFile( localFile.getAbsolutePath() );
						localFileObject.copyFrom( remoteFileNode, Selectors.SELECT_ALL );
					} catch(FileSystemException e) {
						logger.info("Download : " + childNode.getName() + " Error, Continue Download Next Resource");
					}
				}
				
			} else if ( FileType.FILE == remoteFileNode.getType()) {
				// File 改為SafeFile FindBugs錯誤 ----- Kyle 2015/12/04
				logger.info("Remote Node Is File, Begin Download File");
				File localFile = new File( localDirectory, remoteFileNode.getName().getBaseName() );
				FileObject localFileObject = fileSystemManager.resolveFile( localFile.getAbsolutePath() );
				localFileObject.copyFrom( remoteFileNode, Selectors.SELECT_FILES );
			}
			logger.info("Ftp Download Complete");
		} catch (FileSystemException e) {			
			e.printStackTrace();
		}				
	}
	
	public Map<String, byte[]> searchDownload( String url, final String regex ) throws Exception {
		Map<String, byte[]> result = new HashMap<String, byte[]>();
		try {
			String protocol = null;
			if ( FTPProtocol.FTP == info.getProtocol() ) {
				protocol = "ftp://";
			} else if ( FTPProtocol.SFTP == info.getProtocol() ) {
				protocol = "sftp://";
			}
			String realPath = String.format("%s%s:%s@%s/%s", protocol, info.getUser(), info.getPssd(), info.getHost(), url);
			logger.info("Begin Connect To : " + realPath );

			FileObject remoteFileNode = null;
			try {
				remoteFileNode = fileSystemManager.resolveFile(realPath);
			} catch ( Exception e ) {
				logger.info("Could Not Connect To" + realPath);
				logger.info( e );
				throw e;
			}
			
			logger.info("Connect To : " + realPath + " Success");
			
			if ( FileType.FOLDER == remoteFileNode.getType() ) {
				
				
				final FileFilter fileFilter = new FileFilter() {
				    public boolean accept(FileSelectInfo fileInfo) {
				        return fileInfo.getFile().getName().getBaseName().matches(regex);
				    }				                
				};
				
				FileObject[] childNodes = remoteFileNode.findFiles( new FileFilterSelector(fileFilter) );
				
				logger.info("Remote Node Is Directory, Begin Download All Files");
				for( FileObject childNode : childNodes ) {
					logger.info("Begin Download : " + childNode.getName() );
					try {
						if ( FileType.FILE != childNode.getType()) {
							continue;
						}
						byte[] content = FileUtil.getContent(childNode);
						System.out.println(content.length);
						result.put( childNode.getName().getBaseName(), content );
					} catch(FileSystemException e) {
						logger.info("Download : " + childNode.getName() + " Error, Continue Download Next Resource");
					}
				}
				
			} else if ( FileType.FILE == remoteFileNode.getType()) {
				logger.info("Remote Node Is File, Begin Download File");
				byte[] content = FileUtil.getContent(remoteFileNode);
				result.put( remoteFileNode.getName().getBaseName(), content );
			}
			logger.info("Ftp Download Complete");
			return result;
		} catch (FileSystemException e) {			
			logger.info( e );
			throw e;			
		} catch ( Exception e ) {
			logger.info( e );
			throw e;
		}
	}
	
	public void upload( String url, String fileName, byte[] content ) throws Exception {
	 
		/*
		 * 先暫存起來
		 */
	    File tempFile = File.createTempFile("/tempFile", fileName);
	    FileOutputStream fos = new FileOutputStream(tempFile);
	    fos.write(content);
	    fos.flush();
	    fos.close();    
	    
	    try {
	    	
	    	String protocol = null;
			if ( FTPProtocol.FTP == info.getProtocol() ) {
				protocol = "ftp://";
			} else if ( FTPProtocol.SFTP == info.getProtocol() ) {
				protocol = "sftp://";
			}
	    	String realPath = String.format("%s%s:%s@%s/%s", protocol, info.getUser(), info.getPssd(), info.getHost(), url);
			logger.info("Begin Connect To : " + realPath );
			
	        // Create local file object
	        FileObject localFile = fileSystemManager.resolveFile(tempFile.getAbsolutePath());
	 
	        // Create remote file object
	        FileObject remoteFile = fileSystemManager.resolveFile(realPath);
	        FileObject remoteFile2 = fileSystemManager.resolveFile(remoteFile, fileName );
	 
	        // Copy local file to sftp server
	        remoteFile2.copyFrom(localFile, Selectors.SELECT_SELF);
	 
	        System.out.println("File upload success");
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    } finally {

	    }
	}

	/**
	 * 
	 * @author jackson.chang
	 */
	public static class FTPConnectionInfo implements Serializable {
		
		private static final long serialVersionUID = 1L;

		private FTPProtocol protocol;
		
		private String host;
		
		private String port;
		
		private String user;
		
		private String pssd;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPssd() {
			return pssd;
		}

		public void setPssd(String pssd) {
			this.pssd = pssd;
		}

		public FTPProtocol getProtocol() {
			return protocol;
		}

		public void setProtocol(FTPProtocol protocol) {
			this.protocol = protocol;
		}

		public String getPort() {
			return port;
		}

		public void setPort(String port) {
			this.port = port;
		}
		
	}
	
	/**
	 * 
	 * @author jackson.chang
	 */
	public static enum FTPProtocol {
		FTP,
		SFTP
	}
	
}
