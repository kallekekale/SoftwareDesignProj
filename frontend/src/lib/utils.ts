/**
 * Convert time format from "1730" to "17:30" (24-hour format)
 */
export function formatTime(time: string): string {
  const hours = time.substring(0, 2);
  const minutes = time.substring(2, 4);
  return `${hours}:${minutes}`;
}
