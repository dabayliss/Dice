import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.function.ToIntFunction


internal class BaseDiceTest {

    @Test
    fun roll() {
        var bd = BaseDice(1, 4, 6)
        var res = bd.Roll()
        assertNotNull(res)
        assertEquals(3,res.size)
        assertEquals(0x010000,res[0].Key())
        assertEquals(1,res[0].Prob)
        assertEquals(0x0100,res[1].Key())
        assertEquals(2,res[1].Prob)
        assertEquals(0x01,res[2].Key())
        assertEquals(3,res[2].Prob)
        bd = BaseDice(2,4,6)
        res = bd.Roll()
        assertEquals(6,res.size)
        assertEquals(0x020000,res[0].Key())
        assertEquals(1.0/36.0,res[0].Probability())
        assertEquals(0x010100,res[1].Key())
        assertEquals(4,res[1].Prob)
        assertEquals(1.0/9.0,res[1].Probability())
        assertEquals(0x010001,res[2].Key())
        assertEquals(1.5/9.0,res[2].Probability())
        assertEquals(0x000200,res[3].Key())
        assertEquals(1.0/9.0,res[3].Probability())
        assertEquals(0x000101,res[4].Key())
        assertEquals(3/9.0,res[4].Probability())
        assertEquals(0x000002,res[5].Key())
        assertEquals(0.25,res[5].Probability())
        assertEquals(1.0,res.stream().mapToDouble() { it.Probability() }.sum())
        bd = BaseDice(2,4,6)
        res = bd.Roll()
        assertEquals(1.0,res.stream().mapToDouble() { it.Probability() }.sum())
    }

    @Test
    fun grade() {
        val bd = BaseDice(1, 4, 6)
        val rc = BaseDice.RollCombination(byteArrayOf(1),1)
        val gr = bd.Grade(rc)
        assertEquals(0x1,gr.Key())
    }

    @Test
    fun combinations() {
        var bd = BaseDice(1, 4, 6)
        var rc = bd.Combinations()
        assertNotNull(rc)
        assertEquals(6,rc.size)
        for ( i in 0..5 ) {
            assertEquals(i+1,rc[i].Nums[0].toInt())
            assertEquals(1,rc[i].Times)
        }
        bd = BaseDice(2, 4, 6)
        rc = bd.Combinations()
        assertEquals(21,rc.size)
        assertArrayEquals(byteArrayOf(1,1),rc[0].Nums)
        assertEquals(1,rc[0].Times)
        assertArrayEquals(byteArrayOf(2,1),rc[1].Nums)
        assertEquals(2,rc[1].Times)
        assertArrayEquals(byteArrayOf(2,2),rc[2].Nums)
        assertEquals(1,rc[2].Times)
        assertArrayEquals(byteArrayOf(3,1),rc[3].Nums)
        assertEquals(2,rc[3].Times)
        assertArrayEquals(byteArrayOf(3,2),rc[4].Nums)
        assertEquals(2,rc[4].Times)
        assertArrayEquals(byteArrayOf(3,3),rc[5].Nums)
        assertEquals(1,rc[5].Times)
        assertEquals(36,rc.stream().mapToInt({ x->x.Times.toInt() }).sum())
        bd = BaseDice(3, 4, 6)
        rc = bd.Combinations()
        assertEquals(36*6,rc.stream().mapToInt({ x->x.Times.toInt() }).sum())
        assertEquals(56,rc.size)
        assertArrayEquals(byteArrayOf(1,1,1),rc[0].Nums)
        assertEquals(1,rc[0].Times)
        assertArrayEquals(byteArrayOf(2,1,1),rc[1].Nums)
        assertEquals(3,rc[1].Times)
        assertArrayEquals(byteArrayOf(2,2,1),rc[2].Nums)
        assertEquals(3,rc[2].Times)
        assertArrayEquals(byteArrayOf(2,2,2),rc[3].Nums)
        assertEquals(1,rc[3].Times)
        assertArrayEquals(byteArrayOf(3,1,1),rc[4].Nums)
        assertEquals(3,rc[4].Times)
        assertArrayEquals(byteArrayOf(3,2,1),rc[5].Nums)
        assertEquals(6,rc[5].Times)
        bd = BaseDice(6, 4, 6)
        rc = bd.Combinations()
        assertEquals(36*36*36,rc.stream().mapToInt({ x->x.Times.toInt() }).sum())
        assertEquals(462,rc.size)
    }
}