-- 2022-08-22T13:38:46.442Z
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,FileCharset,FormatType,IsActive,IsManualImport,IsMultiLine,Name,Processing,SkipFirstNRows,Updated,UpdatedBy) VALUES (0,540077,0,542207,TO_TIMESTAMP('2022-08-22 16:38:46','YYYY-MM-DD HH24:MI:SS'),100,'utf-8','S','Y','N','N','Import Invoice Candidate','N',1,TO_TIMESTAMP('2022-08-22 16:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:38:50.459Z
UPDATE AD_ImpFormat SET SkipFirstNRows=0,Updated=TO_TIMESTAMP('2022-08-22 16:38:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540077
;

-- 2022-08-22T13:39:12.367Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584199,540077,541780,0,TO_TIMESTAMP('2022-08-22 16:39:12','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N','Y','Rechnungspartner Nr.',10,TO_TIMESTAMP('2022-08-22 16:39:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:39:16.068Z
UPDATE AD_ImpFormat_Row SET StartNo=1,Updated=TO_TIMESTAMP('2022-08-22 16:39:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541780
;

-- 2022-08-22T13:39:57.277Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584168,540077,541781,0,TO_TIMESTAMP('2022-08-22 16:39:57','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N','Y','Rechnungspartner Anschrift ID ',20,TO_TIMESTAMP('2022-08-22 16:39:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:40:00.331Z
UPDATE AD_ImpFormat_Row SET StartNo=2,Updated=TO_TIMESTAMP('2022-08-22 16:40:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541781
;

-- 2022-08-22T13:40:12.422Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584169,540077,541782,0,TO_TIMESTAMP('2022-08-22 16:40:12','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N','Y','Rechnungskontakt ID',30,TO_TIMESTAMP('2022-08-22 16:40:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:40:16.076Z
UPDATE AD_ImpFormat_Row SET StartNo=3,Updated=TO_TIMESTAMP('2022-08-22 16:40:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541782
;

-- 2022-08-22T13:40:31.040Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584170,540077,541783,0,TO_TIMESTAMP('2022-08-22 16:40:31','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N','Y','Produkt Nr',40,TO_TIMESTAMP('2022-08-22 16:40:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:40:32.882Z
UPDATE AD_ImpFormat_Row SET DataType='S',Updated=TO_TIMESTAMP('2022-08-22 16:40:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541783
;

-- 2022-08-22T13:40:35.932Z
UPDATE AD_ImpFormat_Row SET StartNo=4,Updated=TO_TIMESTAMP('2022-08-22 16:40:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541783
;

-- 2022-08-22T13:40:58.569Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584171,540077,541784,0,TO_TIMESTAMP('2022-08-22 16:40:58','YYYY-MM-DD HH24:MI:SS'),100,'D','.','N','Y','Datum beauftragt',50,TO_TIMESTAMP('2022-08-22 16:40:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:41:05.746Z
UPDATE AD_ImpFormat_Row SET DataFormat='YYYY-MM-DD',Updated=TO_TIMESTAMP('2022-08-22 16:41:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541784
;

-- 2022-08-22T13:41:06.926Z
UPDATE AD_ImpFormat_Row SET StartNo=5,Updated=TO_TIMESTAMP('2022-08-22 16:41:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541784
;

-- 2022-08-22T13:41:20.413Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584172,540077,541785,0,TO_TIMESTAMP('2022-08-22 16:41:20','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N','Y','Menge beauftragt',60,TO_TIMESTAMP('2022-08-22 16:41:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:41:22.639Z
UPDATE AD_ImpFormat_Row SET StartNo=6,Updated=TO_TIMESTAMP('2022-08-22 16:41:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541785
;

-- 2022-08-22T13:41:35.899Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584173,540077,541786,0,TO_TIMESTAMP('2022-08-22 16:41:35','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N','Y','Menge geliefert',70,TO_TIMESTAMP('2022-08-22 16:41:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:41:38.202Z
UPDATE AD_ImpFormat_Row SET StartNo=7,Updated=TO_TIMESTAMP('2022-08-22 16:41:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541786
;

-- 2022-08-22T13:41:52.044Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584200,540077,541787,0,TO_TIMESTAMP('2022-08-22 16:41:52','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N','Y','Mengeneinheit',80,TO_TIMESTAMP('2022-08-22 16:41:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:41:54.189Z
UPDATE AD_ImpFormat_Row SET StartNo=8,Updated=TO_TIMESTAMP('2022-08-22 16:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541787
;

-- 2022-08-22T13:42:08.903Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584175,540077,541788,0,TO_TIMESTAMP('2022-08-22 16:42:08','YYYY-MM-DD HH24:MI:SS'),100,'B','.','N','Y','SO Trx',90,TO_TIMESTAMP('2022-08-22 16:42:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:42:10.876Z
UPDATE AD_ImpFormat_Row SET StartNo=9,Updated=TO_TIMESTAMP('2022-08-22 16:42:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541788
;

-- 2022-08-22T13:42:21.675Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584186,540077,541789,0,TO_TIMESTAMP('2022-08-22 16:42:21','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N','Y','Belegart',100,TO_TIMESTAMP('2022-08-22 16:42:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:42:25.422Z
UPDATE AD_ImpFormat_Row SET StartNo=10,Updated=TO_TIMESTAMP('2022-08-22 16:42:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541789
;

-- 2022-08-22T13:42:38.545Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584187,540077,541790,0,TO_TIMESTAMP('2022-08-22 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N','Y','Unterbelegart',110,TO_TIMESTAMP('2022-08-22 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:42:40.191Z
UPDATE AD_ImpFormat_Row SET StartNo=11,Updated=TO_TIMESTAMP('2022-08-22 16:42:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541790
;

-- 2022-08-22T13:42:51.151Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584178,540077,541791,0,TO_TIMESTAMP('2022-08-22 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'D','.','N','Y','Rechnungsdatum ',120,TO_TIMESTAMP('2022-08-22 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:42:56.338Z
UPDATE AD_ImpFormat_Row SET DataFormat='YYYY-MM-DD',Updated=TO_TIMESTAMP('2022-08-22 16:42:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541791
;

-- 2022-08-22T13:42:58.210Z
UPDATE AD_ImpFormat_Row SET StartNo=12,Updated=TO_TIMESTAMP('2022-08-22 16:42:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541791
;

-- 2022-08-22T13:43:08.383Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584179,540077,541792,0,TO_TIMESTAMP('2022-08-22 16:43:08','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N','Y','Beschreibung',130,TO_TIMESTAMP('2022-08-22 16:43:08','YYYY-MM-DD HH24:MI:SS'),100)
;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- 2022-08-22T13:43:10.644Z
UPDATE AD_ImpFormat_Row SET StartNo=13,Updated=TO_TIMESTAMP('2022-08-22 16:43:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541792
;

-- 2022-08-22T13:43:23.970Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584166,540077,541793,0,TO_TIMESTAMP('2022-08-22 16:43:23','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N','Y','Referenz',140,TO_TIMESTAMP('2022-08-22 16:43:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:43:25.849Z
UPDATE AD_ImpFormat_Row SET StartNo=14,Updated=TO_TIMESTAMP('2022-08-22 16:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541793
;

-- 2022-08-22T13:43:46.288Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584177,540077,541794,0,TO_TIMESTAMP('2022-08-22 16:43:46','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N','Y','Rechnungsregel',150,TO_TIMESTAMP('2022-08-22 16:43:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:43:49.345Z
UPDATE AD_ImpFormat_Row SET StartNo=15,Updated=TO_TIMESTAMP('2022-08-22 16:43:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541794
;

