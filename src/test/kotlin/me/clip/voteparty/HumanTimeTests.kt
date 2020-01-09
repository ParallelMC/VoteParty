package me.clip.voteparty

import me.clip.voteparty.util.HumanTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.temporal.ChronoUnit

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
object HumanTimeTests
{
	
	@Test
	internal fun testBasic()
	{
		val time = HumanTime.read("1w")
		
		assertEquals(1,
		             time.getData(ChronoUnit.WEEKS))
		
		assertEquals(7,
		             time.getData(ChronoUnit.DAYS))
	}
	
	@Test
	internal fun testSingle()
	{
		val time = HumanTime.read("1s")
		
		assertEquals(1000,
		             time.getData(ChronoUnit.MILLIS))
	}
}