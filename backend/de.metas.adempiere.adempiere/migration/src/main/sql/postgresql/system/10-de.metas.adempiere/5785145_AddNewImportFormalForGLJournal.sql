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

