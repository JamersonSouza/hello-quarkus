package tech.jamersondev.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import tech.jamersondev.model.Film;
import tech.jamersondev.repository.FilmRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Path("/film")
public class FilmResource {

    @Inject
    FilmRepository filmRepository;

    @GET
    @Path("/{filmId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilme(short filmId) {
        Optional<Film> film = filmRepository.getFilm(filmId);
        return film.isPresent() ? film.get().getTitle() : "Filme não encontrado";
    }

    @GET
    @Path("/pagedFilms/{page}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String filmsPaged(long page, short minLength) {
        return filmRepository.listFilmPaged(page, minLength)
                .map(f -> String.format("%s (%d min)", f.getTitle(), f.getLength()))
                .collect(Collectors.joining("\n")); //operador de junção das collections
    }

    @GET
    @Path("/AllFilms/{maxLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllFilms(short maxLength) {
        return filmRepository.listAllFilms(maxLength)
                .map(f -> String.format("%s (%d max)", f.getTitle(), f.getLength()))
                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("/actors/{startsWith}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getNameActors(String startsWith, short maxLengthFilm) {
        return filmRepository.listActors(startsWith, maxLengthFilm)
                .map(f -> String.format("%s (%d min): %s", f.getTitle(), f.getLength(),
                        f.getActors().stream()
                                .map(a -> String.format("%s %s", a.getFirstName(), a.getLastName()))
                                .collect(Collectors.joining(", "))
                )).collect(Collectors.joining("\n"));

    }

}