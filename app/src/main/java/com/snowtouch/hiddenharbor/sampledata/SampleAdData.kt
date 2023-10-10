package com.snowtouch.hiddenharbor.sampledata

import com.snowtouch.hiddenharbor.R
import com.snowtouch.hiddenharbor.data.model.Ad
import com.snowtouch.hiddenharbor.data.model.AdPrivacyLevel
import com.snowtouch.hiddenharbor.data.model.AdStatus

val photoList = listOf(
    "${R.drawable.sample_ad_image}"
)
val sampleAd: Ad = Ad(
    userId = "037662",
    title = "Spodnie",
    description = "ADFASDFAD sdfsdfsdg asfadfad adfadfadf asdfasfafa",
    price = 32.56,
    category = null,
    groupId = null,
    location = null,
    datePosted = "7-11-2023",
    photoUrls = photoList,
    privacyLevel = AdPrivacyLevel.PUBLIC,
    adStatus = AdStatus.ACTIVE
)
val sampleCardList = listOf(
    sampleAd, sampleAd, sampleAd, sampleAd, sampleAd, sampleAd, sampleAd, sampleAd
)