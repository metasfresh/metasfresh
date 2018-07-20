create table backup.AD_User_before_password_hashing as select * from AD_User where Password is not null;

update AD_User set Password=hash_column_value_if_needed(Password) where Password is not null;


