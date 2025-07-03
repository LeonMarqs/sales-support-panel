package br.com.sales.support.panel.ssp.domain.campaign;

public enum CampaignPlatformEnum {
    GOOGLE_ADS, FACEBOOK_ADS, INSTAGRAM_ADS, LINKEDIN_ADS, TWITTER_ADS, TIKTOK_ADS, SNAPCHAT_ADS, YOUTUBE_ADS, OTHER;

    public static CampaignPlatformEnum getInicialPlatform(CampaignPlatformEnum platform) {
        if (platform == null) {
            return CampaignPlatformEnum.OTHER;
        }
        return platform;
    }
}
