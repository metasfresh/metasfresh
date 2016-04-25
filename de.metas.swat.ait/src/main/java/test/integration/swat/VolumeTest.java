/**
 * 
 */
package test.integration.swat;

/*
 * #%L
 * de.metas.swat.ait
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import org.adempiere.exceptions.DBException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MLocation;
import org.compiere.model.MSequence;
import org.compiere.util.DB;
import org.compiere.util.Env;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;

/**
 * @author teo_sarca
 *
 */
public class VolumeTest
{
	private static final Random s_random = new Random(System.currentTimeMillis());
	
	private static long s_locationStart = -1;
	private static long s_locationCounter = 0;
	
	private static int bpl_seq_id = -1;
	private static int location_seq_id = -1;

	private static MBPartner getCreateBPartner(String value)
	{
		MBPartner bp = MBPartner.get(Env.getCtx(), value);
		if (bp != null)
			return bp;
		bp = new MBPartner(Env.getCtx(), 0, null);
		bp.setValue(value);
		bp.setName(value);
		bp.setDescription("Created by "+VolumeTest.class);
		bp.saveEx();
		return bp;
	}
	
	private static void createLocation(int AD_Client_ID, String city, String zip)
	{
		if (s_locationStart <= 0)
			s_locationStart = System.currentTimeMillis();
		//
		//int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		//int AD_Org_ID = 0;
		String address1 = ""+System.currentTimeMillis();
		int C_Country_ID = 101; // de
		//int C_Location_ID = MSequence.getNextID(AD_Client_ID, "C_Location");
		
		
		final String sql = "INSERT INTO C_Location (" +
				"AD_Client_ID,AD_Org_ID,Address1,C_Country_ID,C_Location_ID" +
				",City,Created,CreatedBy,IsActive,Postal" +
				",Updated,UpdatedBy" +
				") VALUES (" +
				"?,?,?,?" +
				",nextIDFunc("+location_seq_id+",'N')" +
				",?,getDate(),0,'Y',?" +
				", getDate(),0)";
		Object[] params = new Object[]{
				AD_Client_ID, 0, address1, C_Country_ID,
				city, zip,
		};
		DB.executeUpdateEx(sql, params, null);
		s_locationCounter++;
	}
	
	private static void createRandomLocations(MBPartner bp)
	{
		bpl_seq_id = MSequence.get(Env.getCtx(), MBPartnerLocation.Table_Name).getAD_Sequence_ID();
		location_seq_id = MSequence.get(Env.getCtx(), MLocation.Table_Name).getAD_Sequence_ID();

		final int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		
		String sql = "select * from geodb_coordinates";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String city = rs.getString("city");
				String zip = rs.getString("zip");

				int num = s_random.nextInt(30);
				for (int i = 0; i < num; i++)
				{
					createLocation(AD_Client_ID, city, zip);
				}
				createFromLocations(bp.getC_BPartner_ID());
				if (s_locationCounter >= 75000)
				{
					System.out.println("Maximum size reatched - "+s_locationCounter);
					System.exit(0);
				}
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	private static long s_locationLastCounter = -1;
	private static void createFromLocations(int C_BPartner_ID)
	{
		if (s_locationCounter < 0)
		{
			s_locationLastCounter = s_locationCounter;
			return;
		}
		long records = s_locationCounter - s_locationLastCounter;
		if (records < 1000)
		{
			return;
		}
		s_locationLastCounter = s_locationCounter;
		
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		
		final String sql = "INSERT INTO C_BPartner_Location (" +
				"AD_Client_ID,AD_Org_ID,C_BPartner_ID,C_BPartner_Location_ID,C_Location_ID" +
				",Created,CreatedBy,IsActive,IsBillTo,IsPayFrom,IsRemitTo,IsShipTo" +
				",Name" +
				",Updated,UpdatedBy" +
			")" +
			" SELECT " +
				""+AD_Client_ID+",0,"+C_BPartner_ID+",nextIDFunc("+bpl_seq_id+",'N'), l.C_Location_ID" +
				",getDate(),0,'Y','Y','Y','Y','Y'" +
				",'loc'||l.C_Location_ID,getDate(),0"+
			" FROM C_Location l" +
			" WHERE NOT EXISTS (SELECT 1 FROM C_BPartner_Location z WHERE z.C_Location_ID=l.C_Location_ID)" +
			" AND l.AD_Client_ID="+AD_Client_ID
		;
		//
		int no = DB.executeUpdateEx(sql, null);
		System.out.println("CreatedFromLocations #"+no);
		//
		if (s_locationCounter > 0)
		{
			BigDecimal durationUnit = BigDecimal.valueOf(System.currentTimeMillis() - s_locationStart)
									.divide(BigDecimal.valueOf(s_locationCounter), 12, RoundingMode.HALF_UP);
			System.out.println("Generated "+s_locationCounter+"... "+durationUnit +" ms/record");
		}
	}
	
	public static void main(String[] args)
	{
		org.compiere.Adempiere.startup(true);
		
		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, "#AD_Client_ID", 11); // GardenWorld
		//Env.setContext(ctx, "#AD_Org_ID", AD_Org_ID);
		Env.setContext(ctx, "#AD_User_ID", 101); // GardenAdmin
		Env.setContext(ctx, "#AD_Role_ID", 102); // GardenAdmin
		LogManager.setLevel(Level.WARN);
		
		MBPartner bp = getCreateBPartner("de.metas.callcenter.VolumeTest");
		createRandomLocations(bp);
	}
}
