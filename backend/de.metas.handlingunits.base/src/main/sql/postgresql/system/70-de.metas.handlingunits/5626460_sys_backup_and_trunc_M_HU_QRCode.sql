-- NOTE: we are on early phase of M_HU_QRCode implementation so there is no point to migrate existing ones but better just remove them.

create table backup.m_hu_qrcode_bkp20220217 as select * from m_hu_qrcode;

delete from m_hu_qrcode where 1=1;
