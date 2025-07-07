package com.esrabildik.feature.event

sealed interface ProductEvent {
    data object LoadProducts : ProductEvent
}