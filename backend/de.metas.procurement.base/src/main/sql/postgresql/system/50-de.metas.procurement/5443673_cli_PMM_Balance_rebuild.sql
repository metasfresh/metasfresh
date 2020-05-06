-- NOTE: we also need to index C_OrderLine.IsMFProcurement to speed up the updating
create index C_OrderLine_IsMFProcurement on C_OrderLine(IsMFProcurement);

select de_metas_procurement.PMM_Balance_Rebuild();
