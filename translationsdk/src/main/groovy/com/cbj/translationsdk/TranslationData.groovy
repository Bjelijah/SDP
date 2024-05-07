package com.cbj.translationsdk

import org.gradle.api.Project
import org.gradle.api.provider.Property

class TranslationData{
    Property<String> translationFrom
    Property<String> translationTo

    TranslationData(Project p) {
        translationFrom = p.objects.property(String)
        translationTo = p.objects.property(String)
        translationFrom.set("default from")
        translationTo.set("default to")
    }
}