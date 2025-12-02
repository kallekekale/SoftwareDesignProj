package fi.tuni.softwaredesign.search;

import fi.tuni.softwaredesign.shared.domain.dto.response.OpenBreweryDbDistanceResponseDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessDistanceResponseDto;
import java.util.List;

public record BreweryWithRestaurantsDto(
    OpenBreweryDbDistanceResponseDto brewery, List<YelpBusinessDistanceResponseDto> restaurants) {}
