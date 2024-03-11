package ch.uzh.ifi.access.model.dto

data class ContextStatusDto(
    val successfullFiles: List<String>,
    val unsuccessfullFiles: List<String>,
    val timestamp: Int


) {
    constructor() : this(emptyList(), emptyList(),0)
}