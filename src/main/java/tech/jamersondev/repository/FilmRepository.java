package tech.jamersondev.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import tech.jamersondev.model.Film;
import tech.jamersondev.model.Film$;

import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class FilmRepository {

    private static final int PAGE_SIZE = 20;

    @Inject
    JPAStreamer jpaStreamer;

    public Optional<Film> getFilm(short filmId){
        return jpaStreamer.stream(Film.class)
                .filter(Film$.filmId.equal(filmId))
                .findFirst();
    }

    public Stream<Film> listFilmPaged(long page, short minLength){
        return jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(minLength))//filter para filmes que o tamanho seja maior ou igual que o parametro passado
                .sorted(Film$.length) //ordenação dos filmes
                .skip(page * PAGE_SIZE) // pula os primeiros elementos
                .limit(PAGE_SIZE); //definição do limite dos resultados
    }

    public Stream<Film> listAllFilms(short maxLength){
        return jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.length))
                .filter(Film$.length.lessThan(maxLength))// filter para filmes no tamanho igual ou menor ao parametro passado
                .sorted(Film$.length);
    }


}
