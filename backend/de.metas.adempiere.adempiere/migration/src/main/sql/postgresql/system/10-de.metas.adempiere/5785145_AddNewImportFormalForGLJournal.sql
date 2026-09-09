-- 2026-01-20T15:08:39.300Z
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,FileCharset,FormatType,IsActive,IsManualImport,IsMultiLine,Name,Processing,SkipFirstNRows,Updated,UpdatedBy) VALUES (1000000,540102,1000000,599,TO_TIMESTAMP('2026-01-20 15:08:39.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'utf-8','T','Y','N','N','GL Journal - with tax fields','N',1,TO_TIMESTAMP('2026-01-20 15:08:39.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-20T16:15:36.587Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (1000000,9261,540102,542144,1000000,TO_TIMESTAMP('2026-01-20 16:15:36.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C','.','N','Y','DocTypeName',10,TO_TIMESTAMP('2026-01-20 16:15:36.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-20T16:15:47.797Z
UPDATE AD_ImpFormat_Row SET ConstantValue='Journal',Updated=TO_TIMESTAMP('2026-01-20 16:15:47.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542144
;

-- 2026-01-20T16:16:34.248Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (1000000,9238,540102,542145,1000000,TO_TIMESTAMP('2026-01-20 16:16:34.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C','.','N','Y','CategoryName',20,TO_TIMESTAMP('2026-01-20 16:16:34.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-20T16:16:36.414Z
UPDATE AD_ImpFormat_Row SET ConstantValue='Manual',Updated=TO_TIMESTAMP('2026-01-20 16:16:36.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542145
;

-- 2026-01-20T16:17:01.521Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (1000000,9272,540102,542146,1000000,TO_TIMESTAMP('2026-01-20 16:17:01.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','.','N','Y','Iso_Code',30,TO_TIMESTAMP('2026-01-20 16:17:01.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-20T16:17:05.675Z
UPDATE AD_ImpFormat_Row SET StartNo=1,Updated=TO_TIMESTAMP('2026-01-20 16:17:05.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542146
;

-- 2026-01-20T16:18:13.757Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (1000000,9253,540102,542147,1000000,TO_TIMESTAMP('2026-01-20 16:18:13.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','.','N','Y','DateAcct',40,TO_TIMESTAMP('2026-01-20 16:18:13.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-20T16:18:20.156Z
UPDATE AD_ImpFormat_Row SET DataFormat='dd.MM.yyyy',Updated=TO_TIMESTAMP('2026-01-20 16:18:20.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542147
;

-- 2026-01-20T16:18:22.234Z
UPDATE AD_ImpFormat_Row SET StartNo=2,Updated=TO_TIMESTAMP('2026-01-20 16:18:22.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542147
;

-- 2026-01-20T16:20:45.142Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (1000000,9233,540102,542148,1000000,TO_TIMESTAMP('2026-01-20 16:20:45.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','.','N','Y','AccountValueFrom',50,TO_TIMESTAMP('2026-01-20 16:20:45.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-20T16:20:52.282Z
UPDATE AD_ImpFormat_Row SET StartNo=3,Updated=TO_TIMESTAMP('2026-01-20 16:20:52.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542148
;

-- 2026-01-20T16:21:04.770Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (1000000,552772,540102,542149,1000000,TO_TIMESTAMP('2026-01-20 16:21:04.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','.','N','Y','AccountValueTo',60,TO_TIMESTAMP('2026-01-20 16:21:04.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-20T16:21:07.459Z
UPDATE AD_ImpFormat_Row SET StartNo=4,Updated=TO_TIMESTAMP('2026-01-20 16:21:07.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542149
;

-- 2026-01-20T16:22:01.424Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (1000000,9229,540102,542150,1000000,TO_TIMESTAMP('2026-01-20 16:22:01.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','.','N','Y','AmtSourceDr',70,TO_TIMESTAMP('2026-01-20 16:22:01.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-20T16:22:05.356Z
UPDATE AD_ImpFormat_Row SET StartNo=5,Updated=TO_TIMESTAMP('2026-01-20 16:22:05.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542150
;

-- 2026-01-20T16:22:28.813Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (1000000,9281,540102,542151,1000000,TO_TIMESTAMP('2026-01-20 16:22:28.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','.','N','Y','AmtSourceCr',80,TO_TIMESTAMP('2026-01-20 16:22:28.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-20T16:22:34.556Z
UPDATE AD_ImpFormat_Row SET StartNo=6,Updated=TO_TIMESTAMP('2026-01-20 16:22:34.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542151
;

update AD_ImpFormat set AD_Client_ID=0, AD_Org_ID=0 where AD_ImpFormat_ID = 540102;
update AD_ImpFormat_Row set AD_Client_ID=0, AD_Org_ID=0 where AD_ImpFormat_ID = 540102;



-- 2026-01-23T16:30:25.997Z
UPDATE AD_ImpFormat_Row SET SeqNo=160,Updated=TO_TIMESTAMP('2026-01-23 16:30:25.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542144
;

-- 2026-01-23T16:30:28.854Z
UPDATE AD_ImpFormat_Row SET SeqNo=170,Updated=TO_TIMESTAMP('2026-01-23 16:30:28.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542145
;

-- 2026-01-23T16:30:57.525Z
UPDATE AD_ImpFormat_Row SET SeqNo=10,Updated=TO_TIMESTAMP('2026-01-23 16:30:57.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542146
;

-- 2026-01-23T16:30:59.572Z
UPDATE AD_ImpFormat_Row SET SeqNo=20,Updated=TO_TIMESTAMP('2026-01-23 16:30:59.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542147
;

-- 2026-01-23T16:31:01.967Z
UPDATE AD_ImpFormat_Row SET SeqNo=30,Updated=TO_TIMESTAMP('2026-01-23 16:31:01.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542148
;

-- 2026-01-23T16:31:04.063Z
UPDATE AD_ImpFormat_Row SET SeqNo=40,Updated=TO_TIMESTAMP('2026-01-23 16:31:04.061000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542149
;

-- 2026-01-23T16:31:06.706Z
UPDATE AD_ImpFormat_Row SET SeqNo=50,Updated=TO_TIMESTAMP('2026-01-23 16:31:06.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542150
;

-- 2026-01-23T16:31:37.272Z
UPDATE AD_ImpFormat_Row SET SeqNo=60,Updated=TO_TIMESTAMP('2026-01-23 16:31:37.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542151
;

-- 2026-01-23T16:31:57.792Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,591886,540102,542153,0,TO_TIMESTAMP('2026-01-23 16:31:56.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','.','N',0,'Y','TaxAccountValueFrom',70,7,TO_TIMESTAMP('2026-01-23 16:31:56.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-23T16:32:13.179Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,591887,540102,542154,0,TO_TIMESTAMP('2026-01-23 16:32:13.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','.','N',0,'Y','TaxAccountValueFrom',80,8,TO_TIMESTAMP('2026-01-23 16:32:13.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-23T16:32:33.338Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,591884,540102,542155,0,TO_TIMESTAMP('2026-01-23 16:32:33.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','.','N',0,'Y','DR Tax Name',90,9,TO_TIMESTAMP('2026-01-23 16:32:33.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-23T16:32:39.573Z
UPDATE AD_ImpFormat_Row SET Name='Tax Account Value To',Updated=TO_TIMESTAMP('2026-01-23 16:32:39.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542154
;

-- 2026-01-23T16:33:02.798Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,591885,540102,542156,0,TO_TIMESTAMP('2026-01-23 16:33:02.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','.','N',0,'Y','CR Tax Name',100,10,TO_TIMESTAMP('2026-01-23 16:33:02.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-23T16:33:34.923Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,591873,540102,542157,0,TO_TIMESTAMP('2026-01-23 16:33:34.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','.','N',0,'Y','DR Total Tax Amt',110,11,TO_TIMESTAMP('2026-01-23 16:33:34.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-23T16:33:57.921Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,591875,540102,542158,0,TO_TIMESTAMP('2026-01-23 16:33:57.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','.','N',0,'Y','CR Total Tax Amt',120,12,TO_TIMESTAMP('2026-01-23 16:33:57.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-23T16:34:01.833Z
UPDATE AD_ImpFormat_Row SET DataType='N',Updated=TO_TIMESTAMP('2026-01-23 16:34:01.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542157
;

-- 2026-01-23T16:34:42.245Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,591872,540102,542159,0,TO_TIMESTAMP('2026-01-23 16:34:42.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','.','N',0,'Y','ActivityValue',130,13,TO_TIMESTAMP('2026-01-23 16:34:42.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-23T16:34:56.518Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,9286,540102,542160,0,TO_TIMESTAMP('2026-01-23 16:34:56.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','.','N',0,'Y','Description',140,14,TO_TIMESTAMP('2026-01-23 16:34:56.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;