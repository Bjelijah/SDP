package com.cbj.translationsdk

import org.gradle.api.Plugin
import org.gradle.api.Project

class TranslationPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        println '>>> PluginTranslation  apply'
        println ">>> ${project.getProjectDir().absolutePath}   ${project.getBuildDir().absolutePath}   ${project.getRootDir().absolutePath}"


        def ext = project.extensions.create("translationData",TranslationData,project)
        project.tasks.create("startTranslation",TranslationTask){
            from = ext.translationFrom
            to = ext.translationTo
            projectDir = project.objects.property(String)
            projectDir.set(project.getProjectDir().absolutePath)
        }
        println '>>> PluginTranslation  apply  finish'

    }
}