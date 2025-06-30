alter table campaigns
    add status varchar(50) default 'ACTIVE' not null,
    add platform varchar(50) default 'OTHER' not null,
    add end_date date;

alter table campaigns
    add constraint campaigns_platform_check
        check (platform in ('GOOGLE_ADS', 'GOOGLE_ADS', 'FACEBOOK_ADS', 'INSTAGRAM_ADS', 'LINKEDIN_ADS', 'TWITTER_ADS', 'TIKTOK_ADS', 'SNAPCHAT_ADS', 'YOUTUBE_ADS', 'OTHER')),
    add constraint campaigns_status_check
        check (status in ('DRAFT', 'ACTIVE', 'PAUSED', 'COMPLETED', 'ARCHIVED'));
