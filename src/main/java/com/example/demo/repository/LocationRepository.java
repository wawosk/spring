package com.example.demo.repository;

import com.example.demo.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "locations", path = "locations")
public interface LocationRepository extends JpaRepository<Location, Long> {

    // Metody ze stronicowaniem
    // Wyszukiwanie lokalizacji wg kodu kraju z stronicowaniem
    Page<Location> findByCountryCode(String countryCode, Pageable pageable);

    // Wyszukiwanie lokalizacji wg nazwy kraju z stronicowaniem
    Page<Location> findByCountryName(String countryName, Pageable pageable);

    // Wyszukiwanie lokalizacji zawierających w nazwie miasta podany ciąg znaków z stronicowaniem
    Page<Location> findByCityNameContainingIgnoreCase(String cityName, Pageable pageable);

    // Wyszukiwanie wszystkich lokalizacji z stronicowaniem (przydatne dla dużych zbiorów danych)
    Page<Location> findAll(Pageable pageable);

    // Wyszukiwanie lokalizacji wg kodu kraju z sortowaniem po nazwie miasta
    @Query("SELECT l FROM Location l WHERE l.countryCode = :countryCode ORDER BY l.cityName")
    Page<Location> findByCountryCodeOrderByCityName(@Param("countryCode") String countryCode, Pageable pageable);

    // Zaawansowane wyszukiwanie z użyciem @Query i stronicowaniem
    @Query("SELECT l FROM Location l WHERE " +
            "LOWER(l.cityName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(l.countryCode) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Location> searchLocations(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Wyszukiwanie lokalizacji wg zakresu współrzędnych z stronicowaniem
    @Query("SELECT l FROM Location l WHERE " +
            "l.latitude BETWEEN :minLat AND :maxLat AND " +
            "l.longitude BETWEEN :minLon AND :maxLon")
    Page<Location> findByCoordinatesRange(
            @Param("minLat") Double minLat,
            @Param("maxLat") Double maxLat,
            @Param("minLon") Double minLon,
            @Param("maxLon") Double maxLon,
            Pageable pageable);
}