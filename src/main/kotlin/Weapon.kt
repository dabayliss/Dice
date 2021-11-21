class Weapon(val dmg:Int,val critDamage:Int,val bd:BaseDice) {
    fun score(res: BaseDice.Result) : Double {
        return dmg * res.Succ + critDamage * res.Crit.toDouble()
    }
    fun score(res: List<BaseDice.Result>) : Double {
        return res.stream().mapToDouble { it.Probability() * score(it) }.sum()
    }
    fun score() : Double {
        return score(bd.Roll())
    }
}