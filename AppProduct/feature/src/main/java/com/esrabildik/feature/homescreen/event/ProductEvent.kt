package com.esrabildik.feature.homescreen.event

sealed interface ProductEvent {
    data object LoadProducts : ProductEvent
}