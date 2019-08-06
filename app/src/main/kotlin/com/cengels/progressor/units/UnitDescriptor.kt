package com.cengels.progressor.units

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class UnitDescriptor(vararg val value: String)
