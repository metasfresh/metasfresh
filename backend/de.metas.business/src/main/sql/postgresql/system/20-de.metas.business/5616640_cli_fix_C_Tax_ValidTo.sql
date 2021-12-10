
-- it needs to be a date. otherwise we can't change it in the UI
update c_tax set validto='9999-12-31 00:00:00.000000' where validto='9999-12-31 23:59:59.000000';
