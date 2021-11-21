import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class WeaponTest {

    @Test
    fun score() {
        val weapon = Weapon(3,4,BaseDice(1, 4, 6))
        assertEquals(4/6.0+1,weapon.score())
    }

    @Test
    fun testScore() {
    }
}