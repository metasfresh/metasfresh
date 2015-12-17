alter table C_Invoice_Candidate_Recompute set with oids;
create index C_Invoice_Candidate_Recompute_oid on C_Invoice_Candidate_Recompute(oid);
