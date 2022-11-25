package com.jok.commandplanner


var simulatedHour: Int = 0
var simulatedMinute = 0
const val tomorrowString = " tomorrow - "
const val todayString = " today - "
var day = ""
var runMinute: String = ""
var runHour: String = ""

//determine day when minute and hour are specified
fun getSimpleDay(scheduledHour: Int, scheduledMinute: Int): String {
    if (scheduledHour < simulatedHour) {
        return tomorrowString
    } else if (scheduledHour > simulatedHour) {
        return todayString
    } else if (scheduledHour == simulatedHour) {
        return if (scheduledMinute >= simulatedMinute) {
            todayString
        } else {
            tomorrowString
        }
    }
    //should never reach this point
    return ""
}

//determine day when only the minute is specified
fun getDayWhenRanEveryHour(scheduledMinute: Int): String {
    //Day might be tomorrow if it's 11pm, otherwise it will be today
    return if (simulatedHour == 23 && scheduledMinute < simulatedMinute) {
        tomorrowString
    } else {
        todayString
    }
}

// determine day when only the hour is specified
fun getDayWhenRanEveryMinute(scheduledHour: Int): String {
    return if (scheduledHour < simulatedHour) {
        tomorrowString
    } else {
        todayString
    }
}

// determine the hour when only the minute is specified
fun getHourWhenRanEveryHour(scheduledMinute: Int): String {
    return if (day == tomorrowString) {
        "00"
    } else if (scheduledMinute < simulatedMinute) {
        (simulatedHour + 1).toString()
    } else {
        simulatedHour.toString()
    }
}

// determine the minute when only the hour is specified
fun getMinuteWhenRanEveryMinute(scheduledHour: Int): String {
    return if (scheduledHour != simulatedHour) {
        "00"
    } else {
        simulatedMinute.toString()
    }
}

fun runCommandScheduler(timeString: String, commandInput: String) : String {

    val simulatedTime = timeString.split(":")
    simulatedHour = simulatedTime[0].toInt()
    simulatedMinute = simulatedTime[1].toInt()

    val parameterList = commandInput.split("\\s".toRegex()).toTypedArray()
    val minute = parameterList[0]
    val hour = parameterList[1]
    val commandToRun = parameterList[2]

    if (minute == "*" && hour == "*") {
        day = todayString
        runHour = simulatedHour.toString()
        runMinute = simulatedMinute.toString()
    } else if (minute != "*" && hour != "*") {
        day = getSimpleDay(hour.toInt(), minute.toInt())
        runHour = hour
        runMinute = minute
    } else if (minute != "*" && hour == "*") {
        day = getDayWhenRanEveryHour(minute.toInt())
        runHour = getHourWhenRanEveryHour(minute.toInt())
        runMinute = minute
    } else if (minute == "*" && hour != "*") {
        day = getDayWhenRanEveryMinute(hour.toInt())
        runHour = hour
        runMinute = getMinuteWhenRanEveryMinute(hour.toInt())
    }
    if (runMinute.length == 1) {
        runMinute = "0$runMinute"
    }
    return("$runHour:$runMinute$day$commandToRun")
}

fun main() {
    print("Enter the simulated time (format HH:MM): ")
    val timeString = readLine()!!.toString()

    print("Enter the program schedule (e.g. 45 * /bin/run_me_hourly): ")
    val commandInput = readLine()!!.toString()

    print(runCommandScheduler(timeString, commandInput))
}