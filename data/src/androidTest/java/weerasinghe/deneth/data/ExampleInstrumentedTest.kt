package weerasinghe.deneth.data

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import weerasinghe.deneth.data.entities.ActorEntity
import weerasinghe.deneth.data.entities.MovieEntity
import weerasinghe.deneth.data.entities.RatingEntity
import weerasinghe.deneth.data.entities.RoleEntity
import weerasinghe.deneth.data.entities.RoleWithActor
import weerasinghe.deneth.data.entities.RoleWithMovie

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var db: MovieDatabase  // lateinit: "allow it to be nullable, trusts the compiler that it will be initialised before being used

    @Before  // "before test"
    fun setupDb() {
        db = createInMemoryDB(ApplicationProvider.getApplicationContext())
    }

    @After
    @Throws
    fun closeDb() {
        db.close()
    }

    // Some helper functions:
    private suspend fun <T> Flow<List<T>>.checkFlowContains(vararg items: T) {
        test {
            assertThat(awaitItem()).containsExactly(*items)  // *: spread operator: makes list (vararg items) into separate arguments (turn vararg to vararg)
        }
    }
    private suspend fun checkRating(id: String, rating: RatingEntity, vararg movies: MovieEntity) {
        db.dao.getRatingWithMovies(id).let {
            assertThat(it.rating).isEqualTo(rating)
            assertThat(it.movies).containsExactly(*movies)
        }
    }
    private suspend fun checkMovie(id: String, movie: MovieEntity, vararg rwas: RoleWithActor) {
        db.dao.getMovieWithCast(id).let {
            assertThat(it.movie).isEqualTo(movie)
            assertThat(it.rolesWithActors).containsExactly(*rwas)
        }
    }
    private suspend fun checkActor(id: String, actor: ActorEntity, vararg rwms: RoleWithMovie) {
        db.dao.getActorWithFilmography(id).let {
            assertThat(it.actor).isEqualTo(actor)
            assertThat(it.rolesWithMovies).containsExactly(*rwms)
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDB() = runTest {
        db.dao.resetDatabase()
        db.dao.getMovieFlow().checkFlowContains(M1, M2, M3, M4)
        db.dao.getActorFlow().checkFlowContains(A1, A2, A3, A4, A5)
        db.dao.getRatingFlow().checkFlowContains(R0, R1, R2, R3, R4)

        checkRating("r0", R0)
        checkRating("r1", R1)
        checkRating("r2", R2)
        checkRating("r3", R3, M1, M3, M4)
        checkRating("r4", R4, M2)

        checkMovie("m1", M1, RWA11, RWA13)
        checkMovie("m2", M2, RWA21, RWA24)
        checkMovie("m3", M3, RWA32, RWA31)
        checkMovie("m4", M4, RWA42, RWA45)

        checkActor("a1", A1, RWM11, RWM21, RWM31)
        checkActor("a2", A2, RWM32, RWM42)
        checkActor("a3", A3, RWM13)
        checkActor("a4", A4, RWM24)
        checkActor("a5", A5, RWM45)

        db.dao.delete(M1)

        db.dao.getMovieFlow().checkFlowContains(M2, M3, M4)
        db.dao.getActorFlow().checkFlowContains(A1, A2, A3, A4, A5)
        db.dao.getRatingFlow().checkFlowContains(R0, R1, R2, R3, R4)

        checkRating("r0", R0)
        checkRating("r1", R1)
        checkRating("r2", R2)
        checkRating("r3", R3, M3, M4)
        checkRating("r4", R4, M2)

        checkActor("a1", A1, RWM21, RWM31)
        checkActor("a2", A2, RWM32, RWM42)
        checkActor("a3", A3)
        checkActor("a4", A4, RWM24)
        checkActor("a5", A5, RWM45)

        db.dao.delete(A1)

        db.dao.getMovieFlow().checkFlowContains(M2, M3, M4)
        db.dao.getActorFlow().checkFlowContains(A2, A3, A4, A5)
        db.dao.getRatingFlow().checkFlowContains(R0, R1, R2, R3, R4)

        checkRating("r0", R0)
        checkRating("r1", R1)
        checkRating("r2", R2)
        checkRating("r3", R3, M3, M4)
        checkRating("r4", R4, M2)

        checkMovie("m2", M2, RWA24)
        checkMovie("m3", M3, RWA32)
        checkMovie("m4", M4, RWA42, RWA45)

        checkActor("a2", A2, RWM32, RWM42)
        checkActor("a3", A3)
        checkActor("a4", A4, RWM24)
        checkActor("a5", A5, RWM45)

        val updatedM2 = M2.copy(title = "Oh not again")
        val newRole = RoleEntity("m2", "a5", "Comic Relief", 3)
        val newRoleWithActor = RoleWithActor(newRole, A5)
        val newRoleWithMovie = RoleWithMovie(newRole, updatedM2)

        db.dao.update(updatedM2)
        db.dao.insert(newRole)

        db.dao.getMovieFlow().checkFlowContains(updatedM2, M3, M4)
        db.dao.getActorFlow().checkFlowContains(A2, A3, A4, A5)
        db.dao.getRatingFlow().checkFlowContains(R0, R1, R2, R3, R4)

        checkRating("r0", R0)
        checkRating("r1", R1)
        checkRating("r2", R2)
        checkRating("r3", R3, M3, M4)
        checkRating("r4", R4, updatedM2)

        checkMovie("m2", updatedM2, RWA24, newRoleWithActor)
        checkMovie("m3", M3, RWA32)
        checkMovie("m4", M4, RWA42, RWA45)

        checkActor("a2", A2, RWM32, RWM42)
        checkActor("a3", A3)
        checkActor("a4", A4, RWM24.copy(movie = updatedM2))
        checkActor("a5", A5, RWM45, newRoleWithMovie)

    }

    companion object {
        private val M1 = MovieEntity("m1", "The Transporter", "Jason Statham kicks a guy in the face", "r3")
        private val M2 = MovieEntity("m2", "Transporter 2", "Jason Statham kicks a bunch of guys in the face", "r4")
        private val M3 = MovieEntity("m3", "Hobbs and Shaw", "Cars, Explosions and Stuff", "r3")
        private val M4 = MovieEntity("m4", "Jumanji - Welcome to the Jungle", "The Rock smolders", "r3")
        private val A1 = ActorEntity("a1", "Jason Statham")
        private val A2 = ActorEntity("a2", "The Rock")
        private val A3 = ActorEntity("a3", "Shu Qi")
        private val A4 = ActorEntity("a4", "Amber Valletta")
        private val A5 = ActorEntity("a5", "Kevin Hart")
        private val R0 = RatingEntity(id = "r0", name = "Not Rated", description = "Not yet rated")
        private val R1 = RatingEntity(id = "r1", name = "G", description = "General Audiences")
        private val R2 = RatingEntity(id = "r2", name = "PG", description = "Parental Guidance Suggested")
        private val R3 = RatingEntity(id = "r3", name = "PG-13", description = "Unsuitable for those under 13")
        private val R4 = RatingEntity(id = "r4", name = "R", description = "Restricted - 17 and older")
        private val ROLE11 = RoleEntity("m1", "a1", "Frank Martin", 1)
        private val ROLE13 = RoleEntity("m1", "a3", "Lai", 2)
        private val ROLE21 = RoleEntity("m2", "a1", "Frank Martin", 1)
        private val ROLE24 = RoleEntity("m2", "a4", "Audrey Billings", 2)
        private val ROLE32 = RoleEntity("m3", "a2", "Hobbs", 1)
        private val ROLE31 = RoleEntity("m3", "a1", "Shaw", 2)
        private val ROLE42 = RoleEntity("m4", "a2", "Spencer", 1)
        private val ROLE45 = RoleEntity("m4", "a5", "Fridge", 2)
        private val RWA11 = RoleWithActor(ROLE11, A1)
        private val RWA13 = RoleWithActor(ROLE13, A3)
        private val RWA21 = RoleWithActor(ROLE21, A1)
        private val RWA24 = RoleWithActor(ROLE24, A4)
        private val RWA32 = RoleWithActor(ROLE32, A2)
        private val RWA31 = RoleWithActor(ROLE31, A1)
        private val RWA42 = RoleWithActor(ROLE42, A2)
        private val RWA45 = RoleWithActor(ROLE45, A5)
        private val RWM11 = RoleWithMovie(ROLE11, M1)
        private val RWM13 = RoleWithMovie(ROLE13, M1)
        private val RWM21 = RoleWithMovie(ROLE21, M2)
        private val RWM24 = RoleWithMovie(ROLE24, M2)
        private val RWM32 = RoleWithMovie(ROLE32, M3)
        private val RWM31 = RoleWithMovie(ROLE31, M3)
        private val RWM42 = RoleWithMovie(ROLE42, M4)
        private val RWM45 = RoleWithMovie(ROLE45, M4)
    }
}

