package com.cengels.progressor.exceptions

class IllegalConversionException(from: String, to: String) : RuntimeException("Illegal conversion from $from to $to")
