
select db_fail_unless_row_count_greater_zero('update C_Calendar set IsDefault=''Y'', Updated=TO_TIMESTAMP(''2020-08-19 08:00:00'', ''YYYY-MM-DD HH24:MI:SS''), UpdatedBy=100 where c_calendar_id=1000000;');
