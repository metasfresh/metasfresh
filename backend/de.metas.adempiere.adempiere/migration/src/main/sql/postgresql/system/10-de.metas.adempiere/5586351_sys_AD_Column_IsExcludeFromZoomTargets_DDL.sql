alter table ad_column add column IsExcludeFromZoomTargets char(1)
    default 'N'
    check (IsExcludeFromZoomTargets in ('Y', 'N'))
    not null;

