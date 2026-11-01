INSERT INTO AD_Sequence (CurrentNext,IsAudited,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,AD_Client_ID,Name,AD_Org_ID,Description) VALUES (1000000,'N','Y','Y',TO_TIMESTAMP('2026-08-10 15:20:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2026-08-10 15:20:00','YYYY-MM-DD HH24:MI:SS'),100,581987,0,'M_Tag',0,'Table M_Tag')
;

CREATE SEQUENCE IF NOT EXISTS m_tag_seq
    INCREMENT BY 1
    MINVALUE 1000000
    START WITH 1000000
    CACHE 1
    NO CYCLE
;
