
drop view if exists ad_changelog_counts_v;
create view ad_changelog_counts_v as
select l.ad_table_id, t.tablename, l.ad_column_id, c.columnname, count(l.ad_changelog_id) as count,
       'DELETE FROM AD_ChangeLog WHERE AD_Column_ID='||l.ad_column_id||';'||'/*delete '||t.tablename||'.'||c.columnname|| ' AD_ChangeLog records */' as delete_statement,
       'UPDATE AD_Column SET isallowlogging=''N'' WHERE ad_column_id = '||l.ad_column_id||'; /* change AD_Column '||t.tablename||'.'||c.columnname|| ' to not be AD_ChangeLog''ed anymore */'
from ad_changelog l
         join ad_table t on t.ad_table_id=l.ad_table_id
         join ad_column c on c.ad_column_id=l.ad_column_id
group by l.ad_table_id, t.tablename, l.ad_column_id, c.columnname
order by count(l.ad_changelog_id) desc;
comment on view ad_changelog_counts_v is 'Aims at helping to tune changelog settings by counting the current number of AD_ChangeLog records per column';
