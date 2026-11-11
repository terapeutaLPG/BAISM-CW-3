package com.baism.rickandmortyzad3.data

data class CharacterInfo(
    val id: Int,
    val name: String,
    val status: String,
    val species: String
)

data class CharacterPage(
    val next: String?,
    val results: List<CharacterInfo>
)
