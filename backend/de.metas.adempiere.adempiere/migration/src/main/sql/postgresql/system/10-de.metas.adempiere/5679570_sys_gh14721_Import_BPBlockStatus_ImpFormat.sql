-- 2023-02-28T06:33:29.099Z
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,FileCharset,FormatType,IsActive,IsManualImport,IsMultiLine,Name,Processing,SkipFirstNRows,Updated,UpdatedBy) VALUES (0,540080,0,542318,TO_TIMESTAMP('2023-02-28 08:33:29','YYYY-MM-DD HH24:MI:SS'),100,'utf-8','T','Y','N','N','Import BP Block Status','N',1,TO_TIMESTAMP('2023-02-28 08:33:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T06:59:54.370Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,586261,540080,541829,0,TO_TIMESTAMP('2023-02-28 08:59:54','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N','Y','SAP BPartner Code',10,TO_TIMESTAMP('2023-02-28 08:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T07:00:20.099Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,586263,540080,541830,0,TO_TIMESTAMP('2023-02-28 09:00:20','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N','Y','Block Status',20,TO_TIMESTAMP('2023-02-28 09:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T07:00:34.984Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,586262,540080,541831,0,TO_TIMESTAMP('2023-02-28 09:00:34','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N','Y','Reason',30,TO_TIMESTAMP('2023-02-28 09:00:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T07:09:18.281Z
INSERT INTO C_DataImport (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,C_DataImport_ID,Created,CreatedBy,DataImport_ConfigType,InternalName,IsActive,Updated,UpdatedBy) VALUES (1000000,540080,1000000,540023,TO_TIMESTAMP('2023-02-28 09:09:18','YYYY-MM-DD HH24:MI:SS'),100,'S','Block Status','Y',TO_TIMESTAMP('2023-02-28 09:09:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T08:29:11.198Z
UPDATE AD_ImpFormat SET Name='Import BPartner Block Status',Updated=TO_TIMESTAMP('2023-02-28 10:29:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540080
;

-- 2023-02-28T08:30:37.970Z
UPDATE AD_ImpFormat_Row SET Name='GBS ID',Updated=TO_TIMESTAMP('2023-02-28 10:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541829
;

-- 2023-02-28T08:30:43.067Z
UPDATE AD_ImpFormat_Row SET Name='Action to take',Updated=TO_TIMESTAMP('2023-02-28 10:30:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541830
;

-- 2023-02-28T08:30:46.407Z
UPDATE AD_ImpFormat_Row SET Name='Block reason',Updated=TO_TIMESTAMP('2023-02-28 10:30:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541831
;

-- 2023-02-28T08:34:59.049Z
UPDATE C_DataImport SET InternalName='BPartner Block Status',Updated=TO_TIMESTAMP('2023-02-28 10:34:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DataImport_ID=540023
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2023-02-28T10:19:05.994Z
UPDATE AD_ImpFormat_Row SET StartNo=1,Updated=TO_TIMESTAMP('2023-02-28 12:19:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541829
;

-- 2023-02-28T10:19:08.873Z
UPDATE AD_ImpFormat_Row SET StartNo=2,Updated=TO_TIMESTAMP('2023-02-28 12:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541830
;

-- 2023-02-28T10:19:13.905Z
UPDATE AD_ImpFormat_Row SET StartNo=3,Updated=TO_TIMESTAMP('2023-02-28 12:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541831
;

