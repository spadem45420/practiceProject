package com.ftp.test;

import java.io.File;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

public class FtpFileUpload {

	public static void main(String[] args) {
		try {
			File file = new File("D:\\SCI\\Test\\test.txt");
			
			FileSystemManager fsManager = VFS.getManager();
			
			// Create local file object
			FileObject localFile = fsManager.resolveFile(file.getAbsolutePath());
			
			System.out.println(createConnectionString("192.168.110.241", "root", "Zaq1_2wsx", "tmp/test.txt"));
			
			// Create remote file object
			FileObject remoteFile = fsManager.resolveFile(createConnectionString("192.168.110.241:22", "root", "Zaq1_2wsx", "/tmp/test.txt"));
			
			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
			
			System.out.println("File upload success");
			
		} catch (FileSystemException e) {
			e.printStackTrace();
		}
	}

	/**
     * Generates SFTP URL connection String
     * 
     * @param hostName
     *            HostName of the server
     * @param username
     *            UserName to login
     * @param password
     *            Password to login
     * @param remoteFilePath
     *            remoteFilePath. Should contain the entire remote file path -
     *            Directory and Filename with / as separator
     * @return concatenated SFTP URL string
     */
	public static String createConnectionString(String hostName, String username, String password, String remoteFilePath) {
        return "sftp://" + username + ":" + password + "@" + hostName + "/" + remoteFilePath;
    }
	
	/**
     * Method to setup default SFTP config
     * 
     * @return the FileSystemOptions object containing the specified
     *         configuration options
     * @throws FileSystemException
     */
    public static FileSystemOptions createDefaultOptions() throws FileSystemException {
        // Create SFTP options
        FileSystemOptions opts = new FileSystemOptions();

        // SSH Key checking
        SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");

        /*
         * Using the following line will cause VFS to choose File System's Root
         * as VFS's root. If I wanted to use User's home as VFS's root then set
         * 2nd method parameter to "true"
         */
        // Root directory set to user home
        SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false);

        // Timeout is count by Milliseconds
        SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

        return opts;
    }
}
