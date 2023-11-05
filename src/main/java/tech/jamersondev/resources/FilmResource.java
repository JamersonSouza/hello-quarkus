package tech.jamersondev.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import tech.jamersondev.exceptions.ExceptionNotFoundFilm;
import tech.jamersondev.model.Film;
import tech.jamersondev.repository.FilmRepository;

import java.util.Optional;

@Path("/film")
public class FilmResource {

    @Inject
    FilmRepository filmRepository;

    @GET
    @Path("/{filmId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilme(short filmId){
        Optional<Film> film = filmRepository.getFilm(filmId);
        return film.isPresent() ? film.get().getTitle() : "Filme não encontrado";
    }

}
