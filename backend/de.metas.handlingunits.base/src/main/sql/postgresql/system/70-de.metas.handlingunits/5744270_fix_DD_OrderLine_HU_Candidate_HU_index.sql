-- old index:
-- create unique index DD_OrderLine_HU_Candidate_HU on DD_OrderLine_HU_Candidate(M_HU_ID); -- used for searching by M_HU_ID

-- => make it not unique
drop index if exists DD_OrderLine_HU_Candidate_HU;
create index DD_OrderLine_HU_Candidate_HU on DD_OrderLine_HU_Candidate(M_HU_ID); -- used for searching by M_HU_ID

