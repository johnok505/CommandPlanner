package com.jok.commandplanner

import org.junit.Assert
import org.junit.Test

class SchedulerTest {

    @Test
    fun `hour and minute specified`() {
        val expected = "1:30 tomorrow - /bin/run_me_daily"
        val result = runCommandScheduler("16:10", "30 1 /bin/run_me_daily")
        Assert.assertEquals(expected, result)
    }
    @Test
    fun `minute only specified`() {
        val expected = "16:45 today - /bin/run_me_hourly"
        val result = runCommandScheduler("16:10", "45 * /bin/run_me_hourly")
        Assert.assertEquals(expected, result)
    }
    @Test
    fun `nothing specified`() {
        val expected = "16:10 today - /bin/run_me_every_minute"
        val result = runCommandScheduler("16:10", "* * /bin/run_me_every_minute")
        Assert.assertEquals(expected, result)
    }
    @Test
    fun `hour only specified`() {
        val expected = "19:00 today - /bin/run_me_sixty_times"
        val result = runCommandScheduler("16:10", "* 19 /bin/run_me_sixty_times")
        Assert.assertEquals(expected, result)
    }
}