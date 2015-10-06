/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *  Strip Windows (cr/lf) Text files to Unix (cr) Text files
 *
 *  @author     Jorg Janke
 *  @version    $Id: Strip.java,v 1.2 2006/07/30 00:51:06 jjanke Exp $
 */
public class Strip
{
	/** print more info if set to true      */
	private static final boolean VERBOSE = false;

	/**
	 *  Constructor
	 */
	public Strip()
	{
	}   //  Strip

	/*************************************************************************/

	/**
	 *  Strip a directory (and its subdirectories recursively)
	 *  @param directory directory
	 *  @param nameMustContain file name must include characters - e.g. .sh
	 *  (do not include wildcards like *). If null, all files are stripped
	 */
	public void stripDirectory (String directory, String nameMustContain)
	{
		if (directory == null)
			throw new NullPointerException("Strip: directory cannot be null");
		File dir = new File (directory);
		if (!dir.exists() || !dir.isDirectory())
			throw new IllegalArgumentException ("Strip: directory  does not exist or is not a directory: " + dir);

		File[] list = dir.listFiles();
		if (list == null)
			return;
		if (VERBOSE)
			System.out.println("Stripping directory: " + dir);
		for (int i = 0; i < list.length; i++)
		{
			String name = list[i].getAbsolutePath();
			if (list[i].isDirectory())
				stripDirectory (name, nameMustContain);
			else if (nameMustContain == null || name.indexOf(nameMustContain) != -1)
				strip (list[i], null);
		}
	}   //  stripDirectory


	/**
	 *  Strip infile to outfile
	 *  @param infile input file
	 *  @param outfile (can be null)
	 *  @return true if copied
	 */
	public boolean strip (String infile, String outfile)
	{
		if (infile == null)
			throw new NullPointerException("Strip: infile cannot be null");
		File in = new File (infile);
		File out = null;
		if (outfile != null)
			out = new File (outfile);
		//
		return strip (in, out);
	}   //  strip

	/**
	 *  Strip infile to outfile
	 *  @param infile input file
	 *  @param outfile if the output file is null, the infile is renamed to ".bak"
	 *  @return true if copied
	 */
	public boolean strip (File infile, File outfile)
	{
		if (infile == null)
			throw new NullPointerException ("Strip: infile cannot ne null");
		//  infile
		if (!infile.exists() || !infile.isFile())
			throw new IllegalArgumentException ("Strip: infile does not exist or is not a file: " + infile);
		System.out.println("Stripping file: " + infile);

		//  outfile
		if (infile.equals(outfile))
			outfile = null;
		boolean tempfile = false;
		if (outfile == null)
		{
			try
			{
				outfile = File.createTempFile("strip", ".txt");
			}
			catch (IOException ioe)
			{
				System.err.println(ioe);
				return false;
			}
			tempfile = true;
		}
		//
		try
		{
			if (VERBOSE)
				System.out.println("Creating: " + outfile);
			outfile.createNewFile();
		}
		catch (IOException ioe)
		{
			System.err.println(ioe);
			return false;
		}
		if (!outfile.exists() || !outfile.canWrite())
			throw new IllegalArgumentException ("Strip output file cannot be created or written: " + outfile);

		//  copy it
		if (!copy (infile, outfile))
			return false;

		//  rename outfile
		if (tempfile)
		{
			if (VERBOSE)
				System.out.print("Renaming original: " + infile);
			if (!infile.renameTo(new File(infile.getAbsolutePath() + ".bak")))
				System.err.println("Could not rename original file: " + infile);
			if (VERBOSE)
				System.out.println(" - Renaming: " + outfile + " to: " + infile);
			if (!outfile.renameTo(infile))
				System.err.println("Could not rename " + outfile + " to: " + infile);
		}
		return true;
	}   //  strip

	/**
	 *  Copy the file and strip
	 *  @param infile input file
	 *  @param outfile output file
	 *  @return true if success
	 */
	private boolean copy (File infile, File outfile)
	{
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(infile);
		}
		catch (FileNotFoundException fnfe)
		{
			System.err.println(fnfe);
			return false;
		}
		//
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(outfile, false);    //  no append
		}
		catch (FileNotFoundException fnfe)
		{
			System.err.println(fnfe);
			return false;
		}

		int noIn = 0;
		int noOut = 0;
		int noLines = 1;
		try
		{
			int c;
			while ((c = fis.read()) != -1)
			{
				noIn++;
				if (c != 10)    //  lf
				{
					fos.write(c);
					noOut++;
				}
				if (c == 13)    //  cr
					noLines++;
			}
			fis.close();
			fos.close();
		}
		catch (IOException ioe)
		{
			System.err.println(ioe);
			return false;
		}
		System.out.println("  read: " + noIn + ", written: " + noOut + " - lines: " + noLines);
		return true;
	}   //  stripIt

	/*************************************************************************/

	/**
	 *  Strip file in args
	 *  @param args infile outfile
	 */
	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			System.err.println("Syntax: Strip infile outfile");
			System.exit(-1);
		}
		String p2 = null;
		if (args.length > 1)
			p2 = args[1];
		//
		new Strip().strip(args[0], p2);
	}   //  main

}   //  Strip
